const express = require("express");
const router = express.Router();

const {insertActivity, login} = require("../services/activity_services");

router.post("/insertActivity", insertActivity);

module.exports = router;