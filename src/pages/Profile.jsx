
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

function Profile() {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);

  useEffect(() => {
    const savedUser = localStorage.getItem("cinespotUser");

    if (!savedUser) {
      navigate("/login", { replace: true });
      return;
    }

    try {
      setUser(JSON.parse(savedUser));
    } catch {
      localStorage.removeItem("cinespotUser");
      navigate("/login", { replace: true });
    }
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem("cinespotUser");
    navigate("/login", { replace: true });
  };

  if (!user) {
    return <div className="auth-page">Loading profile...</div>;
  }

  const initial = user.name?.trim()?.charAt(0)?.toUpperCase() || "U";

  return (
    <div className="auth-page">
      <div className="auth-card">
        <div className="auth-header">
          <h2>My CineSpot Profile</h2>
          <p>You are logged in!</p>
        </div>

        <div style={{ textAlign: "center", margin: "24px 0" }}>
          <div
            style={{
              width: 100,
              height: 100,
              borderRadius: "50%",
              background: "#e50914",
              color: "#fff",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              fontSize: 42,
              fontWeight: "bold",
              margin: "0 auto 16px"
            }}
          >
            {initial}
          </div>

          <h2>{user.name || "CineSpot User"}</h2>
        </div>

        <p><strong>User ID:</strong> {user.id}</p>
        <p><strong>Email:</strong> {user.email}</p>
        {user.phoneNumber && (
          <p><strong>Phone:</strong> {user.phoneNumber}</p>
        )}

        <Link to="/movies" className="auth-submit"
          style={{ display: "block", textAlign: "center", textDecoration: "none", marginTop: 24 }}>
          Browse Movies
        </Link>

        <button
          type="button"
          className="auth-submit"
          onClick={handleLogout}
          style={{ marginTop: 12 }}
        >
          Logout
        </button>
      </div>
    </div>
  );
}

export default Profile;
