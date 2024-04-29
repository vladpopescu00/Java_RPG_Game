package main;

import entity.Entity;
import tile.Map;

public class CollisionChecker {
    GamePanel gp;
    public Map currentMap;

    public CollisionChecker(GamePanel gp, Map map) {
        this.gp = gp;
        this.currentMap = map;
    }

    // Checks the tiles with which the player will collide next update
    // Maximum 2 possible tiles
    // If collisionOn is true, the player cannot move in the chosen direction
    public void checkTile(Entity entity) {
        int entityLeftX = entity.x + entity.solidArea.x;
        int entityRightX = entity.x + entity.width - entity.solidArea.x;
        int entityTopY = entity.y + entity.solidArea.y;
        int entityBottomY = entity.y + entity.height;

        int entityLeftCol = entityLeftX/GameConstants.TILE_SIZE;
        int entityRightCol = entityRightX/GameConstants.TILE_SIZE;
        int entityTopRow = entityTopY/GameConstants.TILE_SIZE;
        int entityBottomRow = entityBottomY/GameConstants.TILE_SIZE;

        int tileNum1, tileNum2;

        switch(entity.direction) {
            case "up":
                entityTopRow = (entityTopY - entity.speed)/GameConstants.TILE_SIZE;
                tileNum1 = currentMap.layer1.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = currentMap.layer1.mapTileNum[entityRightCol][entityTopRow];

                if(currentMap.layer1.tiles[tileNum1].collision == true || currentMap.layer1.tiles[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomY + entity.speed)/GameConstants.TILE_SIZE;
                tileNum1 = currentMap.layer1.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = currentMap.layer1.mapTileNum[entityRightCol][entityBottomRow];

                if(currentMap.layer1.tiles[tileNum1].collision == true || currentMap.layer1.tiles[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftX - entity.speed)/GameConstants.TILE_SIZE;
                tileNum1 = currentMap.layer1.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = currentMap.layer1.mapTileNum[entityLeftCol][entityBottomRow];

                if(currentMap.layer1.tiles[tileNum1].collision == true || currentMap.layer1.tiles[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightX + entity.speed)/GameConstants.TILE_SIZE;
                tileNum1 = currentMap.layer1.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = currentMap.layer1.mapTileNum[entityRightCol][entityBottomRow];

                if(currentMap.layer1.tiles[tileNum1].collision == true || currentMap.layer1.tiles[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for (int i = 0; i < currentMap.objects.length; i++)
        {
            if(currentMap.objects[i] != null) {
                entity.solidArea.x = entity.x + entity.solidArea.x;
                entity.solidArea.y = entity.y + entity.solidArea.y;

                currentMap.objects[i].solidArea.x = currentMap.objects[i].x + currentMap.objects[i].solidArea.x;
                currentMap.objects[i].solidArea.y = currentMap.objects[i].y + currentMap.objects[i].solidArea.y;

                switch(entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(currentMap.objects[i].solidArea)) {
                            if(currentMap.objects[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(currentMap.objects[i].solidArea)) {
                            if(currentMap.objects[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(currentMap.objects[i].solidArea)) {
//                            if(currentMap.objects[i].collision == true) {
//                                entity.collisionOn = true;
//                            }
                            if(player == true) {
                                index = i;
                            }                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(currentMap.objects[i].solidArea)) {
                            if(currentMap.objects[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(player == true) {
                                index = i;
                            }                        }
                        break;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                currentMap.objects[i].solidArea.x = currentMap.objects[i].solidAreaDefaultX;
                currentMap.objects[i].solidArea.y = currentMap.objects[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    public int checkEntity(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < currentMap.entities.length; i++)
        {
            if(currentMap.entities[i] != null) {
                entity.solidArea.x = entity.x + entity.solidArea.x;
                entity.solidArea.y = entity.y + entity.solidArea.y;

                currentMap.entities[i].solidArea.x = currentMap.entities[i].x + currentMap.entities[i].solidArea.x;
                currentMap.entities[i].solidArea.y = currentMap.entities[i].y + currentMap.entities[i].solidArea.y;

                switch(entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(currentMap.entities[i].solidArea)) {
                            index = i;
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(currentMap.entities[i].solidArea)) {
                            index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(currentMap.entities[i].solidArea)) {
                            index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(currentMap.entities[i].solidArea)) {
                            index = i;
                        }
                        break;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                currentMap.entities[i].solidArea.x = currentMap.entities[i].solidAreaDefaultX;
                currentMap.entities[i].solidArea.y = currentMap.entities[i].solidAreaDefaultY;
            }
        }

        return index;
    }
}
