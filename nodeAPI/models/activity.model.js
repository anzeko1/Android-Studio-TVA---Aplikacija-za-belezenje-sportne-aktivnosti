const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const activitySchema = new Schema({
    idUser: {
        type: String,
    },
    activityName: {
        type: String,
        maxLength: 30,
    },
    activityType: {
        type: String,
        required: true
    },
    activityTypeRecord: {
        type: String,
    },
    activityDate: {
        type: Date,
        required: true
    },
    startActivity: {
        type: String,
    },
    stopActivity: {
        type: String,
    },
    activityLength: {
        type: String,
    },
    lon: {
        type: String,
    },
    lat: {
        type: String,
    },
    description: {
        type: String,
    }
}, {collection: 'activity'})

module.exports.activity = mongoose.model('activity', activitySchema);