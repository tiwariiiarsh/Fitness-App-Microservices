import {
  AppBar,
  Box,
  Button,
  Container,
  Toolbar,
  Typography,
  Paper,
} from "@mui/material";
import FitnessCenterIcon from "@mui/icons-material/FitnessCenter";
import { BrowserRouter, Routes, Route, Navigate } from "react-router";
import { useDispatch } from "react-redux";
import { useContext, useEffect } from "react";
import { AuthContext } from "react-oauth2-code-pkce";
import { setCredentials } from "./slice/authSlice";
import ActivityForm from "./components/ActivityForm";
import ActivityList from "./components/ActivityList";
import ActivityDetail from "./components/ActivityDetails";

const ActivityPage = () => {
  return (
    <Paper
      elevation={6}
      sx={{
        p: 3,
        borderRadius: 3,
        maxWidth: 1030,
        mx: "auto",
        mt: 4,
      }}
    >
      <ActivityForm />
      <ActivityList />
    </Paper>
  );
};

function App() {
  const { token, tokenData, logIn } = useContext(AuthContext);
  const dispatch = useDispatch();

  useEffect(() => {
    if (token && tokenData) {
      dispatch(
        setCredentials({
          token,
          user: tokenData,
          userId: tokenData.sub,
        })
      );
    }
  }, [token, tokenData, dispatch]);

  if (!token) {
    return (
      <Box
        sx={{
          minHeight: "100vh",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          background:
            "linear-gradient(135deg, #667eea 0%, #764ba2 100%)",
        }}
      >
        <Button
          variant="contained"
          size="large"
          startIcon={<FitnessCenterIcon />}
          onClick={logIn}
          sx={{
            px: 4,
            py: 1.5,
            fontSize: "1.1rem",
            borderRadius: 3,
          }}
        >
          Login to Fitness App
        </Button>
      </Box>
    );
  }

  return (
    <BrowserRouter>
      {/* 🔥 NAVBAR */}
    <AppBar
      position="fixed"
      elevation={8}
      sx={{
        top: 0,
        left: 0,
        right: 0,
        background: "linear-gradient(135deg,#ff416c,#ff4b2b)",
      }}
    >
      <Toolbar
        sx={{
          minHeight: 64,
          px: { xs: 2, md: 4 },
          display: "flex",
          alignItems: "center",
        }}
      >
        <FitnessCenterIcon sx={{ mr: 1 }} />
        <Typography variant="h6" fontWeight={700}>
          Fitness AI Dashboard
        </Typography>
      </Toolbar>
    </AppBar>


      {/* 🔥 MAIN CONTENT */}
     <Container maxWidth="xl" sx={{ mt: 10 }}>
        <Routes>
          <Route path="/" element={<Navigate to="/activities" replace />} />
          <Route path="/activities" element={<ActivityPage />} />
          
          <Route path="/activities/:id" element={<ActivityDetail />} />
        </Routes>
      </Container>

    </BrowserRouter>
  );
}

export default App;
