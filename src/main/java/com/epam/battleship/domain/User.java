package com.epam.battleship.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String host;
	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogInDate;

	@Transient
	private Human human;

	protected User() {

	}

	public User(final String name, final String host, final String status,
			final Date lastLogInDate) {
		this.name = name;
		this.host = host;
		this.status = status;
		this.lastLogInDate = lastLogInDate;
	}

	public Human getHuman() {
		return human;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(final String host) {
		this.host = host;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public Date getLastLogInDate() {
		return lastLogInDate;
	}

	public void setLastLoginDate(final Date lastLogInDate) {
		this.lastLogInDate = lastLogInDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", host=" + host
				+ ", status=" + status + ", lastLoginDate=" + lastLogInDate
				+ "]";
	}

	public PositionStatus receiveShot(final int position) {
		return human.receiveShot(position);
	}

	public Position[] getGrid() {
		return human.getGrid();
	}

	public PositionStatus[] getTargetGrid() {
		return human.getTargetGrid();
	}

	public List<Ship> getPlaceableShips() {
		return human.getPlaceableShips();
	}

	public void placeShipsRandomly() {
		human.placeShipsRandomly();
	}

	public boolean isShipPlaceable(final String name, final int position) {
		return human.isShipPlaceable(name, position);
	}

	public void placeShipIntoPosition(final String name, final int position) {
		human.placeShipIntoPosition(name, position);
	}

	public List<Ship> getPlacedShips() {
		return human.getPlacedShips();
	}

	public boolean isFleetDestroyed() {
		return human.isFleetDestroyed();
	}

	public void setShotResultOnPosition(int position, PositionStatus shotResult) {
		human.setShotResultOnPosition(position, shotResult);
	}
}