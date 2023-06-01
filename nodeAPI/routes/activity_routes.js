const express = require("express");
const router = express.Router();

const {insertActivity, insertActivityFrom, getAllActivities, createActivity} = require("../services/activity_services");

router.post("/insertActivity", insertActivity);
router.post("/insertActivityFrom", insertActivityFrom)

router.get("/getAllActivities/:UserID", getAllActivities);
router.post("/createActivity", createActivity);

module.exports = router;