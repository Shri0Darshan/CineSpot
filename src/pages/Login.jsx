
import { useState } from "react";
import {
  useNavigate,
  useLocation,
  Link
} from "react-router-dom";
import { loginUser } from "../services/userService";

function Login() {
  const navigate = useNavigate();
  const location = useLocation();

  const [formData, setFormData] = useState({
    email: "",
    password: ""
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError("");

    if (!formData.email || !formData.password) {
      setError("Please enter email and password.");
      return;
    }

    try {
      setLoading(true);

      const user = await loginUser(formData);

      if (!user || !user.id) {
        setError(
          "Login response does not contain a user ID. Please check your login API response."
        );
        return;
      }

      // Save the logged-in user's details.
      localStorage.setItem(
        "cinespotUser",
        JSON.stringify(user)
      );

      // If login started during booking, return to that flow.
      const bookingState = location.state;

      if (bookingState?.showId && bookingState?.showSeatIds) {
        navigate(
          bookingState.from || `/shows/${bookingState.showId}/seats`,
          {
            replace: true,
            state: bookingState
          }
        );
      } else {
        navigate("/profile", { replace: true });
      }
    } catch (err) {
      console.error("Login error:", err);

      setError(
        err.response?.data?.message ||
        "Invalid email or password."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-container">
        <div className="auth-left">
          <div className="auth-brand">
            🎬 Cine<span>Spot</span>
          </div>

          <p className="auth-small">WELCOME BACK</p>

          <h1>
            Your next
            <span> movie night</span>
            <br />
            is waiting.
          </h1>

          <p className="auth-description">
            Login to CineSpot to discover movies,
            choose your theater, select your seats
            and book your tickets.
          </p>
        </div>

        <div className="auth-card">
          <div className="auth-header">
            <h2>Welcome Back</h2>
            <p>Login to your CineSpot account</p>
          </div>

          {error && (
            <div className="auth-error">
              {error}
            </div>
          )}

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Email</label>
              <input
                type="email"
                name="email"
                placeholder="Enter your email"
                value={formData.email}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label>Password</label>
              <input
                type="password"
                name="password"
                placeholder="Enter your password"
                value={formData.password}
                onChange={handleChange}
                required
              />
            </div>

            <button
              type="submit"
              className="auth-submit"
              disabled={loading}
            >
              {loading ? "Logging in..." : "Login"}
            </button>
          </form>

          <div className="auth-footer">
            Don't have an account?{" "}
            <Link to="/register">Create Account</Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
