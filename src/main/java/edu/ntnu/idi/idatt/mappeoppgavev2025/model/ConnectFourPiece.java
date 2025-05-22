package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

public enum ConnectFourPiece {
  PLAYER_1,
  PLAYER_2;

  public String getPlayerName() {
    return switch (this) {
      case PLAYER_1 -> "Player 1";
      case PLAYER_2 -> "Player 2";
      default -> throw new IllegalStateException("Unexpected value: " + this);
    };
  }
}
