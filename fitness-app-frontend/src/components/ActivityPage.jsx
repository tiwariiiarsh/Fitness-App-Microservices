import { Box, Typography, Grid } from "@mui/material";
import { useState } from "react";
import ActivityForm from "./components/ActivityForm";
import ActivityList from "./components/ActivityList";

const ActivityPage = () => {
  const [refresh, setRefresh] = useState(false);

  return (
    <Box
  sx={{
    px: { xs: 2, md: 5 },
    py: 4,
    minHeight: "100vh",
    background: "linear-gradient(180deg,#0f2027,#203a43,#2c5364)",
  }}
>
  <Typography variant="h3" fontWeight={800} color="white">
    💪 Fitness Dashboard
  </Typography>
  <Typography color="grey.300" mb={5}>
    Track your workouts & get AI-powered insights
  </Typography>

  <Grid container spacing={5}>
    {/* LEFT - FORM */}
    <Grid item xs={12} md={4}>
      <Typography
        variant="h5"
        fontWeight={700}
        color="white"
        mb={2}
      >
        <p style={{ color: "black" }}>➕ Add New Activity</p>
      </Typography>
      <Paper sx={{ p: 3, borderRadius: 3 }}>
        <ActivityForm />
      </Paper>
    </Grid>

    {/* RIGHT - ACTIVITY LIST */}
    <Grid item xs={12} md={8}>
      <Typography
        variant="h5"
        fontWeight={700}
        color="white"
        mb={2}
      >
       <p style={{ color: "black" }}> 🗂 Activity History</p>
      </Typography>
      <ActivityList />
    </Grid>
  </Grid>
</Box>

  );
};

export default ActivityPage;
