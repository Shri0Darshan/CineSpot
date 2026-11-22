
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getShowsByMovieId } from "../services/showService";

function MovieShows() {
    const navigate = useNavigate();
    const { movieId } = useParams();

    const [shows, setShows] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadShows = async () => {
            try {
                setLoading(true);
                setError("");

                const data = await getShowsByMovieId(movieId);

                if (!Array.isArray(data)) {
                    throw new Error("Invalid show data received from backend.");
                }

                setShows(data);
            } catch (err) {
                console.error("Failed to load movie shows:", err);
                setError(
                    err.response?.data?.message ||
                    err.message ||
                    "Unable to load shows."
                );
            } finally {
                setLoading(false);
            }
        };

        loadShows();
    }, [movieId]);

    const formatDateTime = (value) => {
        if (!value) return "Not available";

        const date = new Date(value);

        if (Number.isNaN(date.getTime())) return value;

        return date.toLocaleString("en-IN", {
            dateStyle: "medium",
            timeStyle: "short"
        });
    };

    return (
        <div className="app">
            <nav className="navbar">
                <div
                    className="logo"
                    onClick={() => navigate("/movies")}
                >
                    🎬 Cine<span>Spot</span>
                </div>

                <div className="nav-links">
                    <a
                        href="/movies"
                        onClick={(event) => {
                            event.preventDefault();
                            navigate("/movies");
                        }}
                    >
                        Movies
                    </a>

                    <a
                        href="/theaters"
                        onClick={(event) => {
                            event.preventDefault();
                            navigate("/theaters");
                        }}
                    >
                        Theaters
                    </a>
                </div>

                <button
                    className="login-btn"
                    onClick={() => navigate("/login")}
                >
                    Login
                </button>
            </nav>

            <section className="theater-hero">
                <div>
                    <p className="hero-small">CINESPOT SHOWTIMES</p>
                    <h1>
                        Choose Your <span>Show</span>
                    </h1>
                    <p className="hero-description">
                        Select a showtime to continue to seat selection.
                    </p>
                </div>
            </section>

            <section className="theater-section">
                <div className="section-heading">
                    <p>AVAILABLE SHOWS</p>
                    <h2>Movie Showtimes</h2>
                    <span>Select your preferred theater and time.</span>
                </div>

                {loading && (
                    <div className="page-message">
                        <h2>Loading shows...</h2>
                        <p>Please wait while we contact the backend.</p>
                    </div>
                )}

                {!loading && error && (
                    <div className="page-message">
                        <h2>Unable to load shows</h2>
                        <p>{error}</p>

                        <button
                            className="back-btn"
                            onClick={() => navigate(-1)}
                        >
                            ← Back
                        </button>
                    </div>
                )}

                {!loading && !error && shows.length === 0 && (
                    <div className="page-message">
                        <h2>No shows available</h2>
                        <p>
                            No shows are currently available for this movie.
                        </p>
                        <button
                            className="back-btn"
                            onClick={() => navigate("/movies")}
                        >
                            ← Back to Movies
                        </button>
                    </div>
                )}

                {!loading && !error && shows.length > 0 && (
                    <div className="show-grid">
                        {shows.map((show) => (
                            <div className="show-card" key={show.id}>
                                <div className="show-movie">
                                    <div className="show-icon">🎬</div>

                                    <div>
                                        <p className="theater-label">MOVIE</p>
                                        <h3>
                                            {show.movieTitle ||
                                                show.movie?.title ||
                                                "Movie"}
                                        </h3>
                                    </div>
                                </div>

                                <div className="show-screen">
                                    🎥
                                    <span>
                                        Theater:{" "}
                                        {show.theaterName ||
                                            show.screen?.theater?.name ||
                                            "Not available"}
                                    </span>
                                </div>

                                <div className="show-screen">
                                    🖥️
                                    <span>
                                        Screen:{" "}
                                        {show.screenName ||
                                            show.screen?.name ||
                                            show.screen?.id ||
                                            show.screenId ||
                                            "Not available"}
                                    </span>
                                </div>

                                <div className="show-date">
                                    📅
                                    <span>
                                        {formatDateTime(show.startTime)}
                                    </span>
                                </div>

                                <div className="show-time">
                                    <div>
                                        <small>START</small>
                                        <strong>
                                            {formatDateTime(show.startTime)}
                                        </strong>
                                    </div>

                                    <span>→</span>

                                    <div>
                                        <small>END</small>
                                        <strong>
                                            {formatDateTime(show.endTime)}
                                        </strong>
                                    </div>
                                </div>

                                <button
                                    className="book-btn"
                                    onClick={() =>
                                        navigate(`/shows/${show.id}/seats`)
                                    }
                                >
                                    Select Seats →
                                </button>
                            </div>
                        ))}
                    </div>
                )}

                <button
                    className="back-btn"
                    onClick={() => navigate("/movies")}
                >
                    ← Back to Movies
                </button>
            </section>

            <footer className="footer">
                <div className="footer-logo">🎬 CineSpot</div>
                <div>© 2026 CineSpot. All rights reserved.</div>
            </footer>
        </div>
    );
}

export default MovieShows;
