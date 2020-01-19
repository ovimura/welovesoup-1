package welovesoup;
import battlecode.common.*;

public class Landscaper extends Unit {

    public Landscaper(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        if(rc.getDirtCarrying() == 0){
            //for (Direction dir : Util.directions)
                tryDig(Util.randomDirection());

        }

        MapLocation bestPlaceToBuildWall = null;
        if(hqLoc != null) {
            int lowestElevation = 9999999;
            //MapLocation[] tileNearHQ = {hqLoc.add(Direction.NORTHWEST).add(Direction.NORTHWEST),hqLoc.add(Direction.NORTHWEST).add(Direction.NORTH),hqLoc.add(Direction.NORTH).add(Direction.NORTH),hqLoc.add(Direction.NORTHEAST).add(Direction.NORTH),hqLoc.add(Direction.NORTHEAST).add(Direction.NORTHEAST),hqLoc.add(Direction.EAST).add(Direction.EAST),hqLoc.add(Direction.SOUTHEAST).add(Direction.SOUTHEAST),hqLoc.add(Direction.SOUTH).add(Direction.SOUTH),hqLoc.add(Direction.SOUTHWEST).add(Direction.SOUTHWEST),hqLoc.add(Direction.WEST).add(Direction.WEST)};
            for (Direction dir : Util.directions) {
                MapLocation tileToCheck = hqLoc.add(dir);
                if(rc.getLocation().distanceSquaredTo(tileToCheck) < 4
                        && rc.canDepositDirt(rc.getLocation().directionTo(tileToCheck))) {
                    if (rc.senseElevation(tileToCheck) < lowestElevation) {
                        lowestElevation = rc.senseElevation(tileToCheck);
                        bestPlaceToBuildWall = tileToCheck;
                    }
                }
            }
        }

        if (Math.random() < 0.4){
            // build the wall
            if (bestPlaceToBuildWall != null) {
                rc.depositDirt(rc.getLocation().directionTo(bestPlaceToBuildWall));
                rc.setIndicatorDot(bestPlaceToBuildWall, 0, 255, 0);
            }
        }

        // otherwise try to get to the hq
        if(hqLoc != null){
            nav.goTo(hqLoc);
        } else {
            nav.goTo(Util.randomDirection());
        }


    }

    boolean tryDig(Direction dir) throws GameActionException {
        if(rc.canDigDirt(dir)){
            rc.digDirt(dir);
            rc.setIndicatorDot(rc.getLocation().add(dir), 255, 0, 0);
            return true;
        }
        return false;
    }
}