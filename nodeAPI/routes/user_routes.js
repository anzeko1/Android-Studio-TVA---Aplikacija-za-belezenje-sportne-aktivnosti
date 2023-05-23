const express = require("express");
const {register, login} = require("../services/user_services")

let router = express.Router();

router.post("/register", register);
router.post("/login", login)

module.exports = router;

