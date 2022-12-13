package ooo.sansk.day12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Day12 {
    class Node { 
        int distance;
        Map<Node, Integer> neighbours;
        List<Node> shortestPath;
        int height;
 
        public Node(int height) {
            this.distance = Integer.MAX_VALUE;
            this.shortestPath = new ArrayList<>();
            this.neighbours = new HashMap<>();
            this.height = height;
        }       
    }

    record Edge(int cost, Node destination) {

    }

    record Graph(Node start, Node end, List<Node> nodes) {
        public void calculateShortestPathFromSource(Node start) {
            start.distance = 0;
        
            Set<Node> settledNodes = new HashSet<>();
            Set<Node> unsettledNodes = new HashSet<>();
        
            unsettledNodes.add(start);
        
            while (unsettledNodes.size() != 0) {
                Node currentNode = getLowestDistanceNode(unsettledNodes);
                unsettledNodes.remove(currentNode);
                for (Entry<Node, Integer> adjacencyPair: currentNode.neighbours.entrySet()) {
                    Node adjacentNode = adjacencyPair.getKey();
                    Integer edgeWeight = adjacencyPair.getValue();
                    if (!settledNodes.contains(adjacentNode)) {
                        calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                        unsettledNodes.add(adjacentNode);
                    }
                }
                settledNodes.add(currentNode);
            }
        }

        private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
            Node lowestDistanceNode = null;
            int lowestDistance = Integer.MAX_VALUE;
            for (Node node: unsettledNodes) {
                int nodeDistance = node.distance;
                if (nodeDistance < lowestDistance) {
                    lowestDistance = nodeDistance;
                    lowestDistanceNode = node;
                }
            }
            return lowestDistanceNode;
        }
        
        private void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
            Integer sourceDistance = sourceNode.distance;
            if (sourceDistance + edgeWeigh < evaluationNode.distance) {
                evaluationNode.distance = (sourceDistance + edgeWeigh);
                final var shortestPath = new LinkedList<Node>(sourceNode.shortestPath);
                shortestPath.add(sourceNode);
                evaluationNode.shortestPath = shortestPath;
            }
        }

        public void reset() {
            nodes.forEach(node -> node.distance = Integer.MAX_VALUE);
        }
    }

    private Graph readGraph(String input) {
        final var rows = input.split("\\r?\\n");
        final var nodes = new Node[rows.length][rows[0].length()];
        Node start = null;
        Node end = null;
        for (int x = 0; x < rows.length; x++) {
            final var chars = rows[x].split("");
            for (int y = 0; y < chars.length; y++) {
                final var character = chars[y];
                nodes[x][y] = switch (character) {
                    case "S" -> start = new Node('a');
                    case "E" -> end = new Node('z');
                    default -> new Node(character.charAt(0));
                };
            }
        }

        final var nodeList = new ArrayList<Node>();
        for (int x = 0; x < nodes.length; x++) {
            final var row = nodes[x];
            for (int y = 0; y < row.length; y++) {
                final var node = nodes[x][y];
                if (x + 1 < nodes.length) {
                    final var neighbour = nodes[x + 1][y];
                    if(Math.abs(node.height - neighbour.height) <= 1) {
                        node.neighbours.put(neighbour, 1);
                        neighbour.neighbours.put(node, 1);
                    } else if (node.height > neighbour.height) {
                        node.neighbours.put(neighbour, 1);
                    } else if (node.height < neighbour.height) {
                        neighbour.neighbours.put(node, 1);
                    }
                }                
                if (y + 1 < row.length) {
                    final var neighbour = nodes[x][y + 1];
                    if(Math.abs(node.height - neighbour.height) <= 1) {
                        node.neighbours.put(neighbour, 1);
                        neighbour.neighbours.put(node, 1);
                    } else if (node.height > neighbour.height) {
                        node.neighbours.put(neighbour, 1);
                    } else if (node.height < neighbour.height) {
                        neighbour.neighbours.put(node, 1);
                    }
                }
                nodeList.add(node);
            }
        }

        
        return new Graph(start, end, nodeList);
    }

    public int part1(String inputFile) {
        final var graph = readGraph(inputFile);
        graph.calculateShortestPathFromSource(graph.start());
        return graph.end().distance;
    }

    public int part2(String inputFile) {
        final var graph = readGraph(inputFile);
        var shortestPath = Integer.MAX_VALUE;
        for(var node : graph.nodes()) {
            if (node.height != 'a') {
                continue;
            }
            graph.calculateShortestPathFromSource(node);
            shortestPath = Math.min(shortestPath, graph.end().distance);
            graph.reset();
        }
        return shortestPath;
    }
}
