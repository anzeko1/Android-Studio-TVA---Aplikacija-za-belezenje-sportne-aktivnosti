const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const userSchema = new Schema({
    userName: {
        type: String,
        maxLength: 20,
        required: true
    },
    password: {
        type: String,
        maxLength: 30,
        required: true
    },
    email: {
        type: String,
        required: true
    },
    gender: {
        type: String,
        required: true
    },
    dob: {
        type: Number,
        required: true
    }
}, {collection: 'user'})

module.exports.user = mongoose.model('user', userSchema);