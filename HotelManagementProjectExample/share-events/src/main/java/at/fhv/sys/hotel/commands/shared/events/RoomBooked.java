package at.fhv.sys.hotel.commands.shared.events;

import java.time.LocalDate;

public class RoomBooked {
	private String bookingId;
	private String roomId;
	private String customerId;
	private LocalDate startDate;
	private LocalDate endDate;
	private double totalPrice;

	public RoomBooked() {}

	public RoomBooked(String bookingId, String roomId, String customerId, LocalDate startDate, LocalDate endDate, double totalPrice) {
		this.bookingId = bookingId;
		this.roomId = roomId;
		this.customerId = customerId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.totalPrice = totalPrice;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "RoomBooked{" +
				"bookingId='" + bookingId + '\'' +
				", roomId='" + roomId + '\'' +
				", customerId='" + customerId + '\'' +
				", startDate=" + startDate +
				", endDate=" + endDate +
				", totalPrice=" + totalPrice +
				'}';
	}
}