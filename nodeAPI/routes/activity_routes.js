const express = require("express");
const router = express.Router();

const {insertActivity, insertActivityFrom} = require("../services/activity_services");

router.post("/insertActivity", insertActivity);
router.post("/insertActivityFrom", insertActivityFrom)

module.exports = router;