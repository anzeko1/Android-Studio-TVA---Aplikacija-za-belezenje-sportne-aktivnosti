const express = require("express");
const router = express.Router();

const {predefinedActivities} = require("../services/predefined_activity_services");

router.post("/predefinedActivities", predefinedActivities);

module.exports = router;