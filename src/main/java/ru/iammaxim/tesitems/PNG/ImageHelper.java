package ru.iammaxim.tesitems.PNG;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Maxim on 10.06.2016.
 */
public class ImageHelper {
    public static BufferedImage createImage(int width, int height) {
        BufferedImage heightImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        BufferedImage colorImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        long start = System.currentTimeMillis();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                heightImage.setRGB(x, y, color(1, (float) x / width, (float) y / height, (float) x * y / width / height));
            }
        }
        System.out.println("Image created. Create duration: " + (System.currentTimeMillis() - start) + " ms");
        return heightImage;
    }

    public static void createWorldMapImage(World world, Entity player, int radius) {
        System.out.println("Creating world map image...");

        long start = System.currentTimeMillis();
        IChunkProvider chunkprovider = world.getChunkProvider();
        BufferedImage imageHeight = new BufferedImage((radius * 2 + 1) * 16, (radius * 2 + 1) * 16, BufferedImage.TYPE_INT_ARGB);
        BufferedImage imageColor = new BufferedImage((radius * 2 + 1) * 16, (radius * 2 + 1) * 16, BufferedImage.TYPE_INT_ARGB);

        int chunkCoordX = player.chunkCoordX;
        int chunkCoordZ = player.chunkCoordZ;

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                int finalX = x;
                int finalZ = z;
                player.getServer().addScheduledTask(() -> {
                    int chunkX = chunkCoordX + finalX;
                    int chunkZ = chunkCoordZ + finalZ;
                    Chunk chunk = chunkprovider.provideChunk(chunkX, chunkZ);
                    int[] heightMap = chunk.getHeightMap();

                    for (int x1 = 0; x1 < 16; x1++) {
                        for (int z1 = 0; z1 < 16; z1++) {
                            int height = heightMap[z1 << 4 | x1] - 1;
                            imageHeight.setRGB(finalX * 16 + x1 + radius * 16, finalZ * 16 + z1 + radius * 16, color(255, height, height, height));
                            IBlockState state = chunk.getBlockState(x1, height, z1);
                            int blockColor = state.getMapColor().getMapColor(0);
                            int[] blockColorComponents = {
                                    (blockColor >> 16),
                                    (blockColor >> 8),
                                    blockColor
                            };
//                            float light = (float) state.getLightValue(world, new BlockPos(x1, height - 1, z1)) / 255;
//                            float light = (float) state.getLightValue(world, new BlockPos(chunkX * 16 + finalX * 16 + x1, height - 1, chunkZ * 16 + finalZ * 16 + z1)) / 255;

/*                            System.out.println("light: " + light);

                            for (int i = 0; i < blockColorComponents.length; i++) {
                                blockColorComponents[i] = (int) (blockColorComponents[i] * light);
                            }*/

                            imageColor.setRGB(finalX * 16 + x1 + radius * 16, finalZ * 16 + z1 + radius * 16, color(255, blockColorComponents[0], blockColorComponents[1], blockColorComponents[2]));
                        }
                    }
                });
//                try {
//                    Thread.sleep(20);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }

        player.getServer().addScheduledTask(() -> {
            writeImage(imageHeight, "ChunkImages", "Chunks_height.png");
            writeImage(imageColor, "ChunkImages", "Chunks_color.png");
            System.out.println("World map created. Elapsed time: " + (System.currentTimeMillis() - start));
        });
    }

    private static int color(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    private static int color(float a, float r, float g, float b) {
        return ((int) (a * 255) << 24) | ((int) (r * 255) << 16) | ((int) (g * 255) << 8) | (int) (b * 255);
    }

    public static void writeImage(BufferedImage image, String folder, String name) {
        try {
            File folderFile = new File(folder);
            File file = new File(folder + "/" + name);
            if (!folderFile.exists()) folderFile.mkdirs();
            if (!file.exists()) file.createNewFile();
            ImageIO.write(image, "PNG", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
