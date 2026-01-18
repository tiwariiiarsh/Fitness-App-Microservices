import { createTheme } from "@mui/material/styles";

export const getTheme = (mode) =>
  createTheme({
    palette: {
      mode,
      primary: {
        main: mode === "dark" ? "#90caf9" : "#1976d2",
      },
      secondary: {
        main: mode === "dark" ? "#f48fb1" : "#9c27b0",
      },
      background: {
        default: mode === "dark" ? "#121212" : "#f5f7fa",
        paper: mode === "dark" ? "#1e1e1e" : "#ffffff",
      },
    },
    shape: {
      borderRadius: 12,
    },
    typography: {
      fontFamily: "Inter, Roboto, sans-serif",
      h5: { fontWeight: 600 },
      h6: { fontWeight: 500 },
    },
  });
