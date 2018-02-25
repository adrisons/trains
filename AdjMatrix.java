
/******************************************************************************
 *
 *  Adjacency matrix.
 *  Self-loops are allowed.
 *  Use of BidiMap to map the input key to one value, wich can be used to index
 *  the adjacency matrix
 *
 ******************************************************************************/

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


public class AdjMatrix {

    // Map with the name of the vertices and their assigned id
    private BidiMap<String, Integer> vertexMap;

    private Integer vertex_num = 0;
    private Integer edge_num;
    private Integer[][] adj;

    /**
     * Constructor
     *
     * @param data
     */
    public AdjMatrix(List<String> data) {
        // Create empty adj matrix on this.adj
        createMatrix(data.size());
        data.forEach((s) -> {
            String v_from = s.substring(0, 1);
            String v_to = s.substring(1, 2);
            Integer distance = Integer.valueOf(s.substring(2, 3));

            Integer v1 = addVertexToMap(v_from);
            Integer v2 = addVertexToMap(v_to);

            addEdge(v1, v2, distance);
        });
    }


    /**
     * Create empty graph with V vertices
     *
     * @param vertex_num
     */
    private void createMatrix(int vertex_num) {
        vertexMap = new DualHashBidiMap<String, Integer>();

        if (vertex_num < 0) throw new RuntimeException("Number of vertices must be nonnegative");
        //this.vertex_num = vertex_num;
        this.edge_num = 0;
        this.adj = new Integer[vertex_num][vertex_num];
        for (int i = 0; i < vertex_num; i++) {
            for (int j = 0; j < vertex_num; j++) {
                this.adj[i][j] = -1;
            }
        }
    }

    /**
     * Add a vertex to the map
     *
     * @param key
     * @return the vertex key
     */
    private Integer addVertexToMap(String key) {
        if (!this.vertexMap.containsKey(key)) {
            Integer vertex_id = vertex_num;
            this.vertexMap.put(key, this.vertex_num);
            this.vertex_num++;
            return vertex_id;
        } else {
            return this.vertexMap.get(key);
        }
    }

    /**
     * Add directed edge from v to w with distance dist
     *
     * @param v
     * @param w
     * @param dist
     */
    private void addEdge(int v, int w, int dist) {

        if (this.adj[v][w] == -1) this.edge_num++;
        this.adj[v][w] = dist;
    }

    /**
     * List of neighbors of v
     *
     * @param v
     * @return List of neighbors of v
     */
    private Iterable<Integer> neighbors(int v) {
        return new AdjIterator(v);
    }

    /**
     * @return String representation of the adj matrix
     */
    public String toString() {
        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append(vertex_num).append(" X ").append(edge_num).append(NEWLINE);
        for (int v = 0; v < vertex_num; v++) {
            String v_key = BidiMapUtils.getKey(this.vertexMap, v);
            s.append(v_key).append(": ");
            for (int w : neighbors(v)) {
                String w_key = BidiMapUtils.getKey(this.vertexMap, w);
                s.append(w_key).append("(").append(this.adj[v][w]).append(") ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    /**
     * Calculate the total distance between the ordered items of the list
     *
     * @param vertices
     * @return The total distance
     */
    public Integer calculateDistance(ArrayList<String> vertices) {
        Integer total = 0;
        // There must be at leas 2 vertices to calculate the distance
        if (vertices.size() < 2) {
            return 0;
        }
        // Get the vertices ids
        ArrayList<Integer> ids = new ArrayList<>();
        for (String v : vertices) {
            if (!this.vertexMap.containsKey(v)) {
                throw new RuntimeException("Vertex " + v + " does not exist");
            }
            ids.add(this.vertexMap.get(v));
        }
        // Calculate the distance
        for (Integer i = 0; i < ids.size() - 1; i++) {
            Integer id1 = ids.get(i);
            Integer id2 = ids.get(i + 1);
            if (this.adj[id1][id2] == -1) {
                return -1;
            } else {
                total += this.adj[id1][id2];
            }
        }

        return total;
    }


    /**
     * Support iteration over graph vertices
     */
    private class AdjIterator implements Iterator<Integer>, Iterable<Integer> {
        private final int v;
        private int w = 0;

        AdjIterator(final int v) {
            this.v = v;
        }

        public Iterator<Integer> iterator() {
            return this;
        }

        public boolean hasNext() {
            while (w < vertex_num) {
                if (adj[v][w] != -1) return true;
                w++;
            }
            return false;
        }

        public Integer next() {
            if (hasNext()) return w++;
            else throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}

