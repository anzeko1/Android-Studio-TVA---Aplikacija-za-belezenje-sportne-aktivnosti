
const mongoose = require("mongoose");

const mongoString = process.env.MONGO_STRING
mongoose.connect(mongoString,
    {
    useNewUrlParser: true,
    useUnifiedTopology: true
    });

const conn = mongoose.connection;
conn.on("error", console.error.bind(console, "connection error: "));
conn.once("open", () => {
    console.log("Connected successfully to MongoDB database");
})
