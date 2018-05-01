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

public class FileReader {

    private Set<Triple> tripleSet;

    private Set<Node> leaveSet = new TreeSet<>((c1, c2) -> Integer.compare(c1.getCount(), c2
            .getCount()));

    public FileReader(File input) throws IOException {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(input));) {
            tripleSet = reader.lines()
                    .filter(l -> !l.startsWith("#"))
                    .filter(l -> !l.isEmpty())
                    .map(l -> {
                        String[] firstSplit = l.split(",");
                        String x = firstSplit[0];

                        String[] secondSplit = firstSplit[1].split("|");
                        String y = secondSplit[0];
                        String z = secondSplit[2];

                        return new Triple(Edge.of(Integer.parseInt(x), Integer.parseInt(y)),
                                Node.of(Integer.parseInt(z)));
                    })
                    .collect(Collectors.toSet());
        }

        tripleSet.forEach(t -> {
            getLeaveSet().add(t.getEdge().getFirst());
            getLeaveSet().add(t.getEdge().getSecond());
            getLeaveSet().add(t.getNode());
        });
    }

    public Set<Triple> getTripleSet() {
        return tripleSet;
    }

    public Set<Node> getLeaveSet() {
        return leaveSet;
    }

}
