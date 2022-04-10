package megamek.common;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Ranking {
    
    private final Enumeration<Player> players;
    private final int winnerPlayerId;
    private final int winnerTeamId;
    
    public Ranking(Enumeration<Player> players, int winnerPlayerId, int winnerTeamId) {
        this.players = players;
        this.winnerPlayerId = winnerPlayerId;
        this.winnerTeamId = winnerTeamId;
    }

    /**
     * Update players rating at the end of a game
     */
    public void updatePlayersRating() {
        if (winnerPlayerId == Player.PLAYER_NONE && winnerTeamId == Player.TEAM_NONE) {
            return;
        }

        List<Player> winners = new ArrayList<>();;
        List<Player> losers = new ArrayList<>();

        for (Enumeration<Player> i = players; i.hasMoreElements(); ) {
            final Player player = i.nextElement();
            if (winnerPlayerId != Player.PLAYER_NONE && player.getId() == winnerPlayerId) {
                winners.add(player);
            }
            else if (winnerTeamId != Player.TEAM_NONE && player.getTeam() == winnerTeamId) {
                winners.add(player);
            }
            else {
                losers.add(player);
            }
        }

        double avgWinners = getRatingAverage(winners);
        double avgLosers = getRatingAverage(losers);

        setRatingToPlayers(winners, false, avgLosers);
        setRatingToPlayers(losers, true, avgWinners);
    }

    /**
     * Calculate the average rating of a list of player. Useful to calculate the rating update of the players after a game.
     * @param players A list of winners or losers of a game
     * @return The average rating of a list of player
     */
    private double getRatingAverage(List<Player> players) {
        double sum = 0.0;
        for(Player player: players) {
            sum += player.getRating();
        }
        return sum / players.size();
    }

    /**
     * Calculate and update the rating of the players based on if they won or lost.
     *
     * If they won, but the average rating of the losers was lower than their current rating, their rating will only be
     * increased by a small number. The bigger the difference between the rating and the average of the losers, the smaller the increase.
     *
     * If they won, but the average rating of the losers was higher than their current rating, their rating will be
     * increased by the difference between their rating and the average rating of the losers.
     *
     * If they lost, but the average rating of the winners was higher than their current rating, their rating will only be
     * lowered by a small number. The bigger the difference between the rating and the average of the losers, the smaller the reduction.
     *
     * If they lost, but the average rating of the winners was lower than their current rating, their rating will be
     * lowered by the difference between their rating and the average rating of the losers.
     *
     * @param players A list of winners or losers.
     * @param isLoser True if the list of players are the losers.
     *                False if they are the winners.
     * @param averageEnnemy The average rating of the winners if the players are the losers.
     *                      The average rating of the losers if players are the winners.
     */
    private void setRatingToPlayers(List<Player> players, boolean isLoser, double averageEnnemy) {
        int multiplier = 1;

        if (isLoser) {
            multiplier = -1;
        }

        for(Player player: players) {
            double difference = player.getRating() - averageEnnemy;
            if (multiplier * difference < 0) {
                player.setRating(player.getRating() + multiplier * Math.abs(difference));
            } else {
                double fraction = 0.0;
                if (isLoser) {
                    fraction = player.getRating() / averageEnnemy;
                } else {
                    fraction = averageEnnemy / player.getRating();
                }
                player.setRating(player.getRating() + multiplier * fraction * Math.abs(difference) / 2);
            }
        }
    }
    
}
