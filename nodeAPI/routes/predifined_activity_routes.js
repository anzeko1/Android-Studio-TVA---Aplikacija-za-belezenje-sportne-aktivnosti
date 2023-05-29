const express = require("express");
const router = express.Router();

const {postPredefinedActivities, getActivity} = require("../services/predefined_activity_services");

/*
predefined Aktivnosti mislim narediti tako da jih jaz zapišem v bazo ročno. V android studio dam možnost uporabnikom na izbiro ali
bi oni vnesli svojo aktivnost ali bi izbirali že med ponujenimi aktivnostmi. Če zberejo med ponujenimi aktivnostmi morajo vnesti kateri 
tip aktivnosti bi radi imeli in js vržem nazaj aktivnost. Ko izberejo se vnesejo podatki v obrazec kjer lahko še oni spremenijo aktivnost 
če želijo in shranijo na enak način kot aktivnost ki se ročno vnese.
Najboljši način je da dodam en gumb tam kjer se vnese tip aktivnosti in ko pritisneš gumb pridobis aktivnost

Če bom imel več časa dam lahko tako da lahko zbirajo med več aktivnostmi 
tistega tipa in izberejo samo eno

*/
router.post("/predefinedActivities", postPredefinedActivities);
router.get("/getActivity/:activityType", getActivity);

module.exports = router;