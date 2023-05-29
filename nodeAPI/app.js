const express = require('express')
const cors = require('cors')
const app = express();
require('dotenv').config();
require('./initDB');

app.use(cors());
app.use(express.json());
app.use(express.urlencoded());


const userRouter = require("./routes/user_routes");
const activityRoutes = require("./routes/activity_routes");
const predefinedActivityRouter = require("./routes/predifined_activity_routes");
app.use('/user', userRouter);
app.use('/activity', activityRoutes);
app.use('/predefinedActivity', predefinedActivityRouter);


const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Node API listening on PORT ${PORT}`)
})