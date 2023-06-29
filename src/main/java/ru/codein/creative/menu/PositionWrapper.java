package ru.codein.creative.menu;

import lombok.AllArgsConstructor;

public abstract class PositionWrapper {
    @AllArgsConstructor
    public enum PositionBigChest {
        BOTTOM_LEFT(45),
        BOTTOM_CENTER(49),
        BOTTOM_RIGHT(53),
        TOP_LEFT(0),
        TOP_CENTER(4),
        TOP_RIGHT(8);

        private final int slotId;

        public int getSlotId() {
            return this.slotId;
        }
    }

    @AllArgsConstructor
    public enum PositionSmallChest {
        BOTTOM_LEFT(18),
        BOTTOM_CENTER(22),
        BOTTOM_RIGHT(26),
        TOP_LEFT(0),
        TOP_CENTER(4),
        TOP_RIGHT(8);

        private final int slotId;

        public int getSlotId() {
            return this.slotId;
        }
    }
}
