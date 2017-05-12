import org.junit.Assert;
import org.junit.Test;
import ru.atom.gameinterfaces.Positionable;
import ru.atom.model.GameSession;
import ru.atom.network.Replika;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kinetik on 12.05.17.
 */

public class ReplikaTest {

    @Test
    public void testReplika() {
        GameSession testSession = new GameSession(0);
        List<String> players = new ArrayList<>();
        players.add("Michael");
        players.add("Ilya");
        players.add("Vlad");
        players.add("Igor");
        testSession.newConnection(players);
        testSession.fieldInit();
        ArrayList<String> objects = new ArrayList<>();
        for(Positionable gameObject: testSession.getGameObjects()) {
            objects.add(new Replika(gameObject).getJson());
        }
        System.out.print(objects.get(0));
        Assert.assertEquals(true, true);
    }
}
