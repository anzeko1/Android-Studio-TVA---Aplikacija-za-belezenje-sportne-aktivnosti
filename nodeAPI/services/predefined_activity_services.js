const {predefinedActivity} = require("../models/predefinedActivity.model.js");
//ta spodaj funkcija se uporabi za vnos podatkov v bazo iz snemanja aktivnosti ko se konča aktivnost
const postPredefinedActivities = async (req,res) => {
    const activityType = req.body.activityType;
    const activityLength = req.body.activityLength;
    const description = req.body.description;

    if(activityType == "" || activityLength == "") {
        res.send({"response": "Error - wrong query"});
        console.log("Error - wrong or empty query");
    } else {
    const newPredefinedActivity = new predefinedActivity({
        activityType: activityType,
        activityLength: activityLength,
        description: description,
    })
        await newPredefinedActivity.save()
        .then( () => {
            //takšne so oblike responsa - torej če je kakpna napaka se uporabi spodnja oblika torej: "response" : "Prišlo je do napake x"
            res.send({"response": ` Success: Predefined activity ${activityType} entered`})
        }).catch( (err)=> {
            res.send({"response": `Error: there was a error when inserting a predefined activity: ${err}`})
        })
    }   
}

const getActivity = async (req,res) => {
    if(!req.params.activityType){
        console.log("Nepravilni query ali pa ga sploh ni")
        res.send({"response": "No Query or Wrong query"});
    } else if(req.params.activityType){
        const activityType = req.params.activityType;
        const activity = await predefinedActivity.findOne({ activityType: activityType }).exec()
        if(activity == null) {
            res.send({"response": "There is no such predefined activity"});
            console.log("There is no such predefined activity")
        } else {
            console.log(`Pridobila se je aktivnost ${activityType}`);
            res.send([activity]);
        }
    } else {
        res.send({"response": "There was a error"});
    }
    
}

module.exports = {postPredefinedActivities, getActivity}