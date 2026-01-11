import { useState } from "react";
import {
  Box,
  Button,
  FormControl,
  MenuItem,
  Select,
  TextField,
} from "@mui/material";
import { addActivity } from "../services/api";

const ActivityForm = ({ onActivitiesAdded }) => {
  const [activity, setActivity] = useState({
    type: "RUNNING",
    duration: "",
    caloriesBurned: "",
    additionalMetrices: {},
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await addActivity(activity);
      onActivitiesAdded();
      setActivity({
        type: "RUNNING",
        duration: "",
        caloriesBurned: "",
        additionalMetrices: {},
      });
    } catch (error) {
      console.error("Error adding activity:", error);
    }
  };

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mb: 4 }}>
      <FormControl fullWidth sx={{ mb: 2 }}>
        <h3>Activity Type</h3>
        <Select
          value={activity.type}
          onChange={(e) =>
            setActivity({ ...activity, type: e.target.value })
          }
        >
          <MenuItem value="RUNNING">Running</MenuItem>
          <MenuItem value="WALKING">Walking</MenuItem>
          <MenuItem value="JOGGING">Jogging</MenuItem>
          <MenuItem value="CYCLING">Cycling</MenuItem>
          <MenuItem value="SWIMMING">Swimming</MenuItem>
          <MenuItem value="TREADMILL">Treadmill</MenuItem>

          <MenuItem value="WEIGHT_TRAINING">Weight Training</MenuItem>
          <MenuItem value="BODYWEIGHT_TRAINING">
            Bodyweight Training
          </MenuItem>
          <MenuItem value="CROSSFIT">CrossFit</MenuItem>
          <MenuItem value="FUNCTIONAL_TRAINING">
            Functional Training
          </MenuItem>

          <MenuItem value="YOGA">Yoga</MenuItem>
          <MenuItem value="PILATES">Pilates</MenuItem>
          <MenuItem value="STRETCHING">Stretching</MenuItem>
          <MenuItem value="MEDITATION">Meditation</MenuItem>

          <MenuItem value="HIIT">HIIT</MenuItem>
          <MenuItem value="CARDIO">Cardio</MenuItem>
          <MenuItem value="AEROBICS">Aerobics</MenuItem>
          <MenuItem value="ZUMBA">Zumba</MenuItem>

          <MenuItem value="CRICKET">Cricket</MenuItem>
          <MenuItem value="FOOTBALL">Football</MenuItem>
          <MenuItem value="BADMINTON">Badminton</MenuItem>
          <MenuItem value="BASKETBALL">Basketball</MenuItem>
          <MenuItem value="TENNIS">Tennis</MenuItem>
          <MenuItem value="TABLE_TENNIS">Table Tennis</MenuItem>
          <MenuItem value="VOLLEYBALL">Volleyball</MenuItem>

          <MenuItem value="BOXING">Boxing</MenuItem>
          <MenuItem value="KICKBOXING">Kickboxing</MenuItem>
          <MenuItem value="MARTIAL_ARTS">Martial Arts</MenuItem>
          <MenuItem value="WRESTLING">Wrestling</MenuItem>

          <MenuItem value="CLIMBING">Climbing</MenuItem>
          <MenuItem value="HIKING">Hiking</MenuItem>
          <MenuItem value="TREKKING">Trekking</MenuItem>

          <MenuItem value="DANCE">Dance</MenuItem>
          <MenuItem value="SKIPPING">Skipping Rope</MenuItem>
          <MenuItem value="ROWING">Rowing</MenuItem>

          <MenuItem value="RECOVERY">Recovery</MenuItem>
          <MenuItem value="REST_DAY">Rest Day</MenuItem>

          <MenuItem value="OTHER">Other</MenuItem>
        </Select>
      </FormControl>

      <h3>Duration</h3>
      <TextField
        label="Duration (minutes)"
        type="number"
        sx={{ mb: 2 }}
        value={activity.duration}
        onChange={(e) =>
          setActivity({ ...activity, duration: e.target.value })
        }
        fullWidth
      />

      <h3>Calories Burned</h3>
      <TextField
        label="Calories Burned"
        type="number"
        sx={{ mb: 2 }}
        value={activity.caloriesBurned}
        onChange={(e) =>
          setActivity({ ...activity, caloriesBurned: e.target.value })
        }
        fullWidth
      />

      <Button type="submit" variant="contained" sx={{ mt: 2 }}>
        Add Activity
      </Button>
    </Box>
  );
};

export default ActivityForm;
