package zyx.logic.converter.obj;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExternalModelManager
{

    private static String[] helper;
    private static String[] helper2;
    private ArrayList<VertexData> verticies;
    private ArrayList<TextureData> vertexTextures;
    private ArrayList<NormalData> normals;
    private LinkedList<Face> faces;
    private HashMap<String, VertexTextureRow> map = new HashMap<>();

    public float[] vertexData;
    public int[] elementData;
    
    public void loadData(File file)
    {
        try
        {
            load(file);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ExternalModelManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void load(File file) throws IOException
    {
        verticies = new ArrayList<>();
        vertexTextures = new ArrayList<>();
        normals = new ArrayList<>();
        faces = new LinkedList<>();

        Scanner scan = new Scanner(file);

        String line;
        while (scan.hasNextLine())
        {
            line = scan.nextLine();

            if (line.startsWith("vt "))
            {
                vertexTextures.add(new TextureData(line));
            }
            else if (line.startsWith("v "))
            {
                verticies.add(new VertexData(line));
            }
            else if (line.startsWith("vn "))
            {
                normals.add(new NormalData(line));
            }
            else if (line.startsWith("f "))
            {
                faces.add(createFaceFrom(line));
            }
        }

        buildModel();
    }

    private Face createFaceFrom(String line)
    {
        helper = line.split(" ");

        VertexData[] vertexData = new VertexData[3];
        TextureData[] textureData = new TextureData[3];
        NormalData[] normalData = new NormalData[3];

        for (int i = 1; i < helper.length; i++)
        {
            helper2 = helper[i].split("/");

            int vertexIndex = Integer.parseInt(helper2[0]);
            int textureIndex = Integer.parseInt(helper2[1]);
            int normalIndex = Integer.parseInt(helper2[2]);

            vertexData[i - 1] = verticies.get(vertexIndex - 1);
            textureData[i - 1] = vertexTextures.get(textureIndex - 1);
            normalData[i - 1] = normals.get(normalIndex - 1);
        }

        return new Face(vertexData, textureData, normalData);
    }

    private void buildModel()
    {
        LinkedList<Float> vertexList = new LinkedList<>();
        LinkedList<Integer> elementList = new LinkedList<>();

        int elementCounter = 0;
        Face face;
        while (faces.isEmpty() == false)
        {
            face = faces.removeFirst();

            for (int i = 0; i < 3; i++)
            {
                if (rowExists(face, i))
                {
                    VertexTextureRow row = getRow(face, i);
                    elementList.add(row.elementId);
                }
                else
                {
                    addRow(face, i, elementCounter);
                    vertexList.add(face.vertexData[i].x);
                    vertexList.add(face.vertexData[i].y);
                    vertexList.add(face.vertexData[i].z);
                    vertexList.add(face.textureData[i].u);
                    vertexList.add(face.textureData[i].v);
                    vertexList.add(face.normalData[i].x);
                    vertexList.add(face.normalData[i].y);
                    vertexList.add(face.normalData[i].z);
                    
                    elementList.add(elementCounter);
                    elementCounter++;
                }
            }
        }

        vertexData = toFloatArray(vertexList);
        elementData = toIntArray(elementList);
    }

    private float[] toFloatArray(LinkedList<Float> list)
    {
        float[] data = new float[list.size()];
        for (int i = 0; i < data.length; i++)
        {
            data[i] = list.removeFirst();
        }

        return data;
    }

    private int[] toIntArray(LinkedList<Integer> list)
    {
        int[] data = new int[list.size()];
        for (int i = 0; i < data.length; i++)
        {
            data[i] = list.removeFirst();
        }

        return data;
    }

    private boolean rowExists(Face face, int i)
    {
        return map.containsKey(face.textureData[i].definition() + face.vertexData[i].definition() + face.normalData[i].definition());
    }

    private VertexTextureRow getRow(Face face, int i)
    {
        return map.get(face.textureData[i].definition() + face.vertexData[i].definition() + face.normalData[i].definition());
    }

    private void addRow(Face face, int i, int element)
    {
        VertexTextureRow row = new VertexTextureRow(element, face.textureData[i], face.vertexData[i], face.normalData[i]);
        map.put(row.definition(), row);
    }

    private static class Face
    {

        private final VertexData[] vertexData;
        private final TextureData[] textureData;
        private final NormalData[] normalData;

        public Face(VertexData[] vertexData, TextureData[] textureData, NormalData[] normalData)
        {
            this.vertexData = vertexData;
            this.textureData = textureData;
            this.normalData = normalData;
        }
    }

    private static class VertexTextureRow
    {

        private final int elementId;
        private final TextureData tex;
        private final VertexData vert;
        private final NormalData normal;

        public VertexTextureRow(int elementId, TextureData tex, VertexData vert, NormalData normal)
        {
            this.elementId = elementId;
            this.tex = tex;
            this.vert = vert;
            this.normal = normal;
        }

        private String definition()
        {
            return vert.definition() + tex.definition() + normal.definition();
        }
    }

    private static class TextureData
    {

        private static String[] helper;
        private final float u, v;

        public TextureData(String line)
        {
            helper = line.split(" ");

            u = Float.parseFloat(helper[1]);
            v = Float.parseFloat(helper[2]);
        }

        private String definition()
        {
            return String.format("%s%s", u, v);
        }
    }

    private static class VertexData
    {

        private static String[] helper;
        private final float x, y, z;

        public VertexData(String line)
        {
            helper = line.split(" ");

            x = Float.parseFloat(helper[2]);
            y = Float.parseFloat(helper[3]);
            z = Float.parseFloat(helper[4]);
        }

        private String definition()
        {
            return String.format("%s%s%s", x, y, z);
        }
    }

    private static class NormalData
    {

        private static String[] helper;
        private final float x, y, z;

        public NormalData(String line)
        {
            helper = line.split(" ");

            x = Float.parseFloat(helper[1]);
            y = Float.parseFloat(helper[2]);
            z = Float.parseFloat(helper[3]);
        }

        private String definition()
        {
            return String.format("%s%s%s", x, y, z);
        }
    }
}
