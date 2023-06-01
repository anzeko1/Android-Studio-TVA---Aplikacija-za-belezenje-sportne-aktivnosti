const {activity} = require("../models/activity.model.js");
//ta spodaj funkcija se uporabi za vnos podatkov v bazo iz snemanja aktivnosti ko se konča aktivnost
const insertActivity = async (req,res) => {
    res.send("test");
}

//ta funkcija pa se uporabi za vnos aktivnosti iz forme
const insertActivityFrom = async (req, res) => {
    const idUser = req.body.idUser;
    const activityName = req.body.activityName;
    const activityType = req.body.activityType;
    const activityTypeRecord = req.body.activityTypeRecord;
    const activityDate = req.body.activityDate;
    const activityLength = req.body.activityLength;
    const description = req.body.description;

    const newActivity = new activity({
        idUser: idUser,
        activityName: activityName,
        activityType: activityType,
        activityTypeRecord: activityTypeRecord,
        activityDate: activityDate,
        activityLength: activityLength,
        description: description
    })

    await newActivity.save()
    .then( () => {
        //takšne so oblike responsa - torej če je kakpna napaka se uporabi spodnja oblika torej: "response" : "Prišlo je do napake x"
        res.send({"response": ` Success: Activity with name: ${activityName} inserted`})
    }).catch( (err)=> {
        res.send({"response": `Error: there was a error when inserting activity: ${err}`})
    })
}

// GET ALL ACTIVITIES
const getAllActivities = async (req, res) => {
  try {
    const activities = await activity.find();
    res.json(activities);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Server error' });
  }
};

// POST ACTIVITY
const createActivity = async (req, res) => {
  try {
    const newActivity = new activity(req.body);
    const savedActivity = await newActivity.save();
    res.json(savedActivity);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Server error' });
  }
};

module.exports = {insertActivity, insertActivityFrom, getAllActivities, createActivity}