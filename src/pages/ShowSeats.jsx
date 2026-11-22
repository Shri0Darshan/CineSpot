
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../services/api";

function ShowSeats() {
    const navigate = useNavigate();
    const { showId } = useParams();

    const [seats, setSeats] = useState([]);
    const [selectedSeats, setSelectedSeats] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        loadSeats();
    }, [showId]);

    const loadSeats = async () => {
        try {
            setLoading(true);
            setError("");

            const response = await api.get(
                `/show-seats/show/${showId}`
            );

            console.log("Seats received from backend:", response.data);

            if (!Array.isArray(response.data)) {
                throw new Error(
                    "The backend did not return a list of seats."
                );
            }

            setSeats(response.data);
            setSelectedSeats([]);
        } catch (err) {
            console.error("Failed to load seats:", err);

            setError(
                err.response?.data?.message ||
                err.message ||
                "Unable to load seats."
            );
        } finally {
            setLoading(false);
        }
    };

    const getStatus = (seat) => {
        return String(seat.status || "AVAILABLE").toUpperCase();
    };

    const isAvailable = (seat) => {
        return getStatus(seat) === "AVAILABLE";
    };

    const isSelected = (seatId) => {
        return selectedSeats.includes(seatId);
    };

    const toggleSeat = (seat) => {
        if (!isAvailable(seat)) {
            return;
        }

        setSelectedSeats((previous) => {
            if (previous.includes(seat.id)) {
                return previous.filter((id) => id !== seat.id);
            }

            return [...previous, seat.id];
        });
    };

    const getSeatClass = (seat) => {
        if (isSelected(seat.id)) {
            return "seat selected";
        }

        if (getStatus(seat) === "BOOKED") {
            return "seat booked";
        }

        if (getStatus(seat) === "LOCKED") {
            return "seat locked";
        }

        if (!isAvailable(seat)) {
            return "seat unavailable";
        }

        return "seat available";
    };

    const totalAmount = seats
        .filter((seat) => selectedSeats.includes(seat.id))
        .reduce((total, seat) => total + Number(seat.price || 0), 0);

    const handleContinue = () => {
        if (selectedSeats.length === 0) {
            alert("Please select at least one available seat.");
            return;
        }

        // The IDs here are ShowSeat IDs, as required by the booking API.
        navigate("/login", {
            state: {
                from: `/shows/${showId}/seats`,
                showId: Number(showId),
                showSeatIds: selectedSeats,
                totalAmount
            }
        });

        alert(
            "Your seat selection is ready. Sign in to continue. " +
            "Booking and payment will be connected after we confirm " +
            "how your login page stores the logged-in user."
        );
    };

    return (
        <div className="app">
            <nav className="navbar">
                <div
                    className="logo"
                    onClick={() => navigate("/movies")}
                    style={{ cursor: "pointer" }}
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
                    <p className="hero-small">CINESPOT BOOKING</p>
                    <h1>
                        Select Your <span>Seats</span>
                    </h1>
                    <p className="hero-description">
                        Choose available seats for your movie.
                    </p>
                </div>
            </section>

            <section className="theater-section">
                {loading && (
                    <div className="page-message">
                        <h2>Loading seats...</h2>
                        <p>Fetching seat availability from the backend.</p>
                    </div>
                )}

                {!loading && error && (
                    <div className="page-message">
                        <h2>Unable to load seats</h2>
                        <p>{error}</p>

                        <button
                            className="book-btn"
                            onClick={loadSeats}
                        >
                            Try Again
                        </button>

                        <button
                            className="back-btn"
                            onClick={() => navigate(-1)}
                        >
                            ← Back
                        </button>
                    </div>
                )}

                {!loading && !error && (
                    <>
                        <div className="section-heading">
                            <p>SHOW ID: {showId}</p>
                            <h2>Choose Your Seats</h2>
                            <span>
                                Select one or more available seats.
                            </span>
                        </div>

                        <div className="seat-legend">
                            <span>
                                <i className="seat available" /> Available
                            </span>
                            <span>
                                <i className="seat selected" /> Selected
                            </span>
                            <span>
                                <i className="seat booked" /> Booked
                            </span>
                            <span>
                                <i className="seat locked" /> Locked
                            </span>
                        </div>

                        <div className="screen-container">
                            <div className="cinema-screen">
                                SCREEN
                            </div>
                            <p>All eyes this way</p>
                        </div>

                        {seats.length === 0 ? (
                            <div className="page-message">
                                <h3>No seats found</h3>
                                <p>
                                    No seats were returned for this show.
                                    Please check that show seats have been
                                    created in the backend.
                                </p>
                            </div>
                        ) : (
                            <div className="seat-grid">
                                {seats.map((seat) => (
                                    <button
                                        key={seat.id}
                                        type="button"
                                        className={getSeatClass(seat)}
                                        disabled={!isAvailable(seat)}
                                        title={
                                            `Seat ${seat.seatNumber || seat.id}` +
                                            ` - ${getStatus(seat)}` +
                                            ` - ₹${seat.price ?? 0}`
                                        }
                                        onClick={() => toggleSeat(seat)}
                                    >
                                        {seat.seatNumber || seat.id}
                                    </button>
                                ))}
                            </div>
                        )}

                        <div className="booking-summary">
                            <div>
                                <p>Selected Seats</p>
                                <strong>
                                    {selectedSeats.length > 0
                                        ? seats
                                            .filter((seat) =>
                                                selectedSeats.includes(seat.id)
                                            )
                                            .map((seat) =>
                                                seat.seatNumber || seat.id
                                            )
                                            .join(", ")
                                        : "None"}
                                </strong>
                            </div>

                            <div>
                                <p>Total Amount</p>
                                <strong>₹{totalAmount.toFixed(2)}</strong>
                            </div>

                            <button
                                className="book-btn"
                                disabled={
                                    seats.length === 0 ||
                                    selectedSeats.length === 0
                                }
                                onClick={handleContinue}
                            >
                                Continue →
                            </button>
                        </div>

                        <button
                            className="back-btn"
                            onClick={() => navigate(-1)}
                        >
                            ← Back to Shows
                        </button>
                    </>
                )}
            </section>

            <footer className="footer">
                <div className="footer-logo">🎬 CineSpot</div>
                <div>© 2026 CineSpot. All rights reserved.</div>
            </footer>
        </div>
    );
}

export default ShowSeats;
