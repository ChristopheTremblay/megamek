package megamek.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class RankingTest {

    @Test
    public void testUpdatePlayersRating() {
        String playerName = "jefke";
        Player player1 = new Player(1, playerName);
        player1.setRating(50);

        playerName = "Jeanke";
        Player player2 = new Player(2, playerName);
        player2.setRating(125);

        playerName = "Paul";
        Player player3 = new Player(3, playerName);
        player3.setRating(75);

        playerName = "Steven";
        Player player4 = new Player(4, playerName);
        player4.setRating(100);

        List<Player> playersList = new ArrayList<>();
        playersList.add(player1);
        playersList.add(player2);
        playersList.add(player3);
        playersList.add(player4);

        Enumeration<Player> playersEnumeration = Collections.enumeration(playersList);
        Ranking ranking = new Ranking(playersEnumeration, player1.getId(), Player.TEAM_NONE);
        ranking.updatePlayersRating();

        assertTrue(player1.getRating() > 50);
        assertTrue(player2.getRating() < 125);
        assertTrue(player3.getRating() < 75);
        assertTrue(player4.getRating() < 100);

        assertEquals(100, player1.getRating(), 0.0);
        assertEquals(50, player2.getRating(), 0.0);
        assertEquals(50, player3.getRating(), 0.0);
        assertEquals(50, player4.getRating(), 0.0);
    }

    @Test
    public void testUpdatePlayersRatingDraw() {
        String playerName = "jefke";
        Player player1 = new Player(1, playerName);
        player1.setRating(50);

        playerName = "Jeanke";
        Player player2 = new Player(2, playerName);
        player2.setRating(125);

        playerName = "Paul";
        Player player3 = new Player(3, playerName);
        player3.setRating(75);

        playerName = "Steven";
        Player player4 = new Player(4, playerName);
        player4.setRating(100);

        List<Player> playersList = new ArrayList<>();
        playersList.add(player1);
        playersList.add(player2);
        playersList.add(player3);
        playersList.add(player4);

        Enumeration<Player> playersEnumeration = Collections.enumeration(playersList);
        Ranking ranking = new Ranking(playersEnumeration, Player.PLAYER_NONE, Player.TEAM_NONE);
        ranking.updatePlayersRating();

        assertEquals(50, player1.getRating(), 0.0);
        assertEquals(125, player2.getRating(), 0.0);
        assertEquals(75, player3.getRating(), 0.0);
        assertEquals(100, player4.getRating(), 0.0);
    }

    @Test
    public void testUpdatePlayersRatingWinnerAlreadyHasBetterRanking() {
        String playerName = "jefke";
        Player player1 = new Player(1, playerName);
        player1.setRating(500);

        playerName = "Jeanke";
        Player player2 = new Player(2, playerName);
        player2.setRating(125);

        playerName = "Paul";
        Player player3 = new Player(3, playerName);
        player3.setRating(75);

        playerName = "Steven";
        Player player4 = new Player(4, playerName);
        player4.setRating(100);

        List<Player> playersList = new ArrayList<>();
        playersList.add(player1);
        playersList.add(player2);
        playersList.add(player3);
        playersList.add(player4);

        Enumeration<Player> playersEnumeration = Collections.enumeration(playersList);
        Ranking ranking = new Ranking(playersEnumeration, player1.getId(), Player.TEAM_NONE);
        ranking.updatePlayersRating();

        assertTrue(player1.getRating() > 500);
        assertTrue(player2.getRating() < 125);
        assertTrue(player3.getRating() < 75);
        assertTrue(player4.getRating() < 100);

        assertEquals(540, player1.getRating(), 0.0);
        assertEquals(78.125, player2.getRating(), 0.0);
        assertEquals(43.125, player3.getRating(), 0.0);
        assertEquals(60, player4.getRating(), 0.0);
    }

    @Test
    public void testUpdateTeamRating() {
        String playerName = "jefke";
        Player player1 = new Player(1, playerName);
        player1.setRating(50);

        playerName = "Jeanke";
        Player player2 = new Player(2, playerName);
        player2.setRating(125);

        playerName = "Paul";
        Player player3 = new Player(3, playerName);
        player3.setRating(75);

        playerName = "Steven";
        Player player4 = new Player(4, playerName);
        player4.setRating(100);

        Team team1 = new Team(1);
        Team team2 = new Team(2);
        player1.setTeam(team1.getId());
        player2.setTeam(team1.getId());
        player3.setTeam(team2.getId());
        player4.setTeam(team2.getId());

        List<Player> playersList = new ArrayList<>();
        playersList.add(player1);
        playersList.add(player2);
        playersList.add(player3);
        playersList.add(player4);

        Enumeration<Player> playersEnumeration = Collections.enumeration(playersList);
        Ranking ranking = new Ranking(playersEnumeration, player1.getId(), team1.getId());
        ranking.updatePlayersRating();

        assertTrue(player1.getRating() > 50);
        assertTrue(player2.getRating() > 125);
        assertTrue(player3.getRating() < 75);
        assertTrue(player4.getRating() < 100);
    }
}
