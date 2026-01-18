import {
  Card,
  CardContent,
  Typography,
  TextField,
  Select,
  MenuItem,
  Button,
  Box,
} from "@mui/material";
import { useState } from "react";
import { addActivity } from "../services/api";

const ActivityForm = ({ onActivitiesAdded }) => {
  const [activity, setActivity] = useState({
    type: "RUNNING",
    duration: "",
    caloriesBurned: "",
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    await addActivity(activity);
    onActivitiesAdded();
    setActivity({ type: "RUNNING", duration: "", caloriesBurned: "" });
  };

  return (
    <Card
      sx={{
        borderRadius: 4,
        background: "rgba(255,255,255,0.12)",
        backdropFilter: "blur(10px)",
        color: "white",
        marginBottom: 10,
      }}
    >
      <CardContent>
        <Typography
        fontFamily="'Poppins', sans-serif"
        variant="h4"
        fontWeight={700}
        color="Black"
        mb={2}
      >
       <p style={{ color: "black" }}>➕ Add Activity</p>
      </Typography>
      

        <Select
          fullWidth
          value={activity.type}
          sx={{ mb: 2, bgcolor: "white", borderRadius: 2 }}
          onChange={(e) =>
            setActivity({ ...activity, type: e.target.value })
          }
        >
          {["RUNNING","WALKING","CYCLING","GYM","YOGA","HIIT"].map((a) => (
            <MenuItem key={a} value={a}>{a}</MenuItem>
          ))}
        </Select>

        <TextField
          fullWidth
          label="Duration (min)"
          type="number"
          sx={{ mb: 2, bgcolor: "white", borderRadius: 2 }}
          value={activity.duration}
          onChange={(e) =>
            setActivity({ ...activity, duration: e.target.value })
          }
        />

        <TextField
          fullWidth
          label="Calories"
          type="number"
          sx={{ mb: 3, bgcolor: "white", borderRadius: 2 }}
          value={activity.caloriesBurned}
          onChange={(e) =>
            setActivity({ ...activity, caloriesBurned: e.target.value })
          }
        />

        <Button
          fullWidth
          size="large"
          variant="contained"
          onClick={handleSubmit}
          sx={{
            borderRadius: 3,
            py: 1.2,
            background: "linear-gradient(135deg,#ff512f,#dd2476)",
          }}
        >
          Save Activity
        </Button>
      </CardContent>
    </Card>
  );
};

export default ActivityForm;
