import { Grid, Card, CardContent, Typography, Box } from "@mui/material";
import { useEffect, useState } from "react";
import { getActivities } from "../services/api";
import { useNavigate } from "react-router";

const ActivityList = ({ refresh }) => {
  const [activities, setActivities] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    getActivities().then((res) => setActivities(res.data));
  }, [refresh]);

  return (
    <div>
      <Typography
  variant="h4"
  sx={{
    fontFamily: "'Poppins', sans-serif",
    fontWeight: 700,
    color: "Black",
    letterSpacing: "0.6px",
    mb: 2,
  }}
>
  🗂 Activity History
</Typography>

    <Grid container spacing={4}>
  {[...activities]
    .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
    .map((a) => (
      <Grid item xs={12} sm={6} key={a.id}>
        <Card
          onClick={() => navigate(`/activities/${a.id}`)}
          sx={{
            height: "100%",
            borderRadius: 4,
            p: 1,
            cursor: "pointer",
            background:
              "linear-gradient(135deg,#43cea2,#185a9d)",
            color: "white",
            transition: "all 0.3s ease",
            "&:hover": {
              transform: "translateY(-6px)",
              boxShadow: "0 16px 32px rgba(0,0,0,0.4)",
            },
          }}
        >
          <CardContent sx={{ p: 3 }}>
            <Typography variant="h6" fontWeight={700}>
              {a.type}
            </Typography>

            <Typography mt={1}>⏱ {a.duration} min</Typography>
            <Typography>🔥 {a.caloriesBurned} kcal</Typography>

            <Typography
              mt={2}
              fontSize={13}
              sx={{ opacity: 0.85 }}
            >
              View AI Recommendation →
            </Typography>
          </CardContent>
        </Card>
      </Grid>
    ))}
</Grid>
</div>

  );
};

export default ActivityList;
