const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const predefinedActivitySchema = new Schema({
    activityType: {
        type: String,
        required: true
    },
    activityLength: {
        type: Number,
        required: true
    },
    description: {
        type: String,
        required: true
    }
}, {collection: 'predefinedActivity'})

module.exports.predefinedActivity = mongoose.model('predefinedActivity', predefinedActivitySchema);