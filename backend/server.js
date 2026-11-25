const express = require("express");
const http = require("http");
const { Server } = require("socket.io");
const crypto = require("crypto");

const app = express();
const server = http.createServer(app);

const io = new Server(server, {
    cors: {
        origin: "*"
    }
});

const rooms = new Map();

app.get("/", (req, res) => {
    res.send("Ludo Pro Online Server Running!");
});

function generateCode() {
    return crypto.randomBytes(3)
        .toString("hex")
        .toUpperCase();
}

function broadcastRooms() {

    const publicRooms = [];

    for (const room of rooms.values()) {

        if (
            room.type === "public" &&
            room.players.length < 4
        ) {
            publicRooms.push({
                code: room.code,
                players: room.players.length
            });
        }
    }

    io.emit("public_rooms", publicRooms);
}

function joinRoom(socket, room, name) {

    if (room.players.length >= 4) {
        socket.emit("error_message", "Room is full");
        return;
    }

    const player = {
        id: socket.id,
        name: String(name || "Player").slice(0, 20)
    };

    room.players.push(player);

    socket.join(room.code);

    socket.emit("room_joined", {
        code: room.code,
        players: room.players
    });

    io.to(room.code).emit("room_state", {
        code: room.code,
        players: room.players
    });

    broadcastRooms();
}

io.on("connection", socket => {

    console.log("Player connected:", socket.id);

    socket.emit("public_rooms", []);

    socket.on("create_private", data => {

        const code = generateCode();

        const room = {
            code,
            type: "private",
            players: []
        };

        rooms.set(code, room);

        joinRoom(socket, room, data.name);
    });

    socket.on("join_private", data => {

        const code = String(data.code || "")
            .toUpperCase();

        const room = rooms.get(code);

        if (!room || room.type !== "private") {
            socket.emit(
                "error_message",
                "Invalid Room Code"
            );
            return;
        }

        joinRoom(socket, room, data.name);
    });

    socket.on("join_public", data => {

        let room = [...rooms.values()].find(
            r => r.type === "public" &&
                 r.players.length < 4
        );

        if (!room) {

            room = {
                code: generateCode(),
                type: "public",
                players: []
            };

            rooms.set(room.code, room);
        }

        joinRoom(socket, room, data.name);
    });

    socket.on("roll_dice", () => {

        const room = [...rooms.values()].find(
            r => r.players.some(
                p => p.id === socket.id
            )
        );

        if (!room) return;

        // निष्पक्ष Server-side Dice
        const value = crypto.randomInt(1, 7);

        io.to(room.code).emit("dice_result", {
            value,
            by: socket.id
        });
    });

    socket.on("disconnect", () => {

        for (const [code, room] of rooms) {

            room.players = room.players.filter(
                p => p.id !== socket.id
            );

            if (room.players.length === 0) {
                rooms.delete(code);
            }
        }

        broadcastRooms();

        console.log("Player disconnected:", socket.id);
    });
});

const PORT = process.env.PORT || 3000;

server.listen(PORT, "0.0.0.0", () => {
    console.log("Server running on port " + PORT);
});
