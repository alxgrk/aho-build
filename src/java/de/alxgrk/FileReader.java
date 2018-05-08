package de.alxgrk;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import de.alxgrk.model.Edge;
import de.alxgrk.model.Node;
import de.alxgrk.model.Triple;
import lombok.Getter;

@Getter
public class FileReader {

    private Set<Triple> tripleSet;

    private TreeSet<Node> leaveSet = new TreeSet<>((c1, c2) -> Integer.compare(c1.getCount(), c2
            .getCount()));

    public FileReader(File input) throws IOException {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(input));) {
            tripleSet = extractTriples(reader);
        }

        filterLeaves();
    }

    private Set<Triple> extractTriples(BufferedReader reader) {
        return reader.lines()
                .filter(l -> !l.startsWith("#"))
                .filter(l -> !l.isEmpty())
                .map(l -> {
                    String[] firstSplit = l.split(",");
                    Integer x = Integer.parseInt(firstSplit[0]);

                    String yAndZ = firstSplit[1];
                    String[] secondSplit = yAndZ.split("\\|");
                    Integer y = Integer.parseInt(secondSplit[0]);
                    Integer z = Integer.parseInt(secondSplit[1]);

                    return new Triple(Edge.of(x, y), Node.of(z));
                })
                .collect(Collectors.toSet());
    }

    private void filterLeaves() {
        tripleSet.forEach(t -> {
            getLeaveSet().add(t.getEdge().getFirst());
            getLeaveSet().add(t.getEdge().getSecond());
            getLeaveSet().add(t.getNode());
        });
    }

}
