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
    activityDuration: {
        type: String,
    },
    activityDistance: {
        type: String,
    },
    coordinates: [
        {
            lat: {
                type: Number,
                required: true
            },
            long: {
                type: Number,
                required: true
            }
        }
    ],
    description: {
        type: String,
    }

}, { collection: 'activity' });

module.exports.activity = mongoose.model('activity', activitySchema);
