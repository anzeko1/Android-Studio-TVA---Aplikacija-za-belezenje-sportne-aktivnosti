const {user} = require("../models/user.model.js");
const register = async (req,res) => {
    const userName = req.body.userName;
    const password = req.body.password;
    const email = req.body.email;
    const gender = req.body.gender;
    const dob = req.body.dob;

    const newUser = new user({
        userName: userName,
        password: password,
        email: email,
        gender: gender,
        dob: dob
    })

    await newUser.save()
    .then( () => {
        res.send({"response": ` Success: User with username: ${userName} registered`})
    }).catch( (err)=> {
        res.send({"response": `Error: there was a error when inserting a user: ${err}`})
    })

}
const login = (req,res) => {
    register.send("login")
}

module.exports = {register, login};