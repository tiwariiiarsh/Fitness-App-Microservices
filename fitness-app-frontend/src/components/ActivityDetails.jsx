import { Box, Card, CardContent, Divider, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { getActivityDetail } from "../services/api";

const Section = ({ title, items }) => (
  <>
    <Typography variant="h6" mt={2}>{title}</Typography>
    {items?.map((item, i) => (
      <Typography key={i} sx={{ ml: 2 }}>• {item}</Typography>
    ))}
    <Divider sx={{ my: 2 }} />
  </>
);

const ActivityDetail = () => {
  const { id } = useParams();
  const [activity, setActivity] = useState(null);

  useEffect(() => {
    getActivityDetail(id).then(res => setActivity(res.data));
  }, [id]);

  if (!activity) return <Typography>Loading...</Typography>;

  return (
   <Box sx={{ maxWidth: 900, mx: "auto", mt: 5 }}>
  <Card elevation={10} sx={{ borderRadius: 4 }}>
    <CardContent sx={{ p: 4 }}>
      <Typography
        variant="h4" 
        color="Black"
      fontWeight={800} 
      gutterBottom>
        🧠 AI Activity Report
      </Typography>

      <Typography>🏃 {activity.type}</Typography>
      <Typography>⏱ {activity.duration} min</Typography>
      <Typography>🔥 {activity.caloriesBurned} kcal</Typography>

      <Divider sx={{ my: 3 }} />

      <Typography variant="h5" padding={1}>📊 Analysis</Typography>
      <Typography>{activity.recommendation}</Typography>

      <Section title="🚀 Improvements" padding={1} items={activity.improvements} />
      
      <Section title="💡 Suggestions" items={activity.suggestions} />
      <Section title="⚠ Safety Tips" items={activity.safety} />
    </CardContent>
  </Card>
</Box>

  );
};

export default ActivityDetail;
