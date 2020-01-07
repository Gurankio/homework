/*
 * CGT - Cursor (Turtle) Graphics in a Terminal.
 */

#ifndef CGT
#define CGT

#define CGT_SPACE      " "
#define CGT_HORIZONTAL "qq"
#define CGT_VERTICAL   "x"
#define CGT_CORNER_TL  "lq"
#define CGT_CORNER_TR  "k"
#define CGT_CORNER_BL  "mq"
#define CGT_CORNER_BR  "j"

#include <string.h>

#include "vts.c"
#include "sleep.c"

// // Prototypes
void cgt(int, int, char *);

// // Functions
void cgt(int xIn, int yIn, char *command) {
  static int addX = 0, addY = 1;
  static int x = 1, y = 1;

  if (xIn != -1) x = xIn > 0 ? xIn : 1;

  if (yIn != -1) y = yIn > 0 ? yIn : 1;

  vts_activateCommands();
  vts_lineDrawingSet();

  for (int i = 0; i < strlen(command); ++i) {
    vts_xy(x, y);
    switch (command[i]) {
      case 'F':

        if (addX == 0) puts(CGT_VERTICAL);
        else puts(CGT_HORIZONTAL);

        x += addX;
        y += addY;
        break;

      case 'B':

        if (addX == 0) puts(CGT_VERTICAL);
        else puts(CGT_HORIZONTAL);

        x -= addX;
        y -= addY;
        break;

      case '+':

        if (addX == 0 && addY == 1) {
          addX = 2, addY = 0;
          puts(CGT_CORNER_BL);
        } else if (addX == 2 && addY == 0) {
          addX = 0, addY = -1;
          puts(CGT_CORNER_BR);
        } else if (addX == 0 && addY == -1) {
          addX = -2, addY = 0;
          puts(CGT_CORNER_TR);
        } else if (addX == -2 && addY == 0) {
          addX = 0, addY = 1;
          puts(CGT_CORNER_TL);
        }

        x += addX;
        y += addY;
        break;

      case '-':

        if (addX == 0 && addY == 1) {
          addX = -2, addY = 0;
          puts(CGT_CORNER_BR);
        } else if (addX == 2 && addY == 0) {
          addX = 0, addY = 1;
          puts(CGT_CORNER_TR);
        } else if (addX == 0 && addY == -1) {
          addX = 2, addY = 0;
          puts(CGT_CORNER_TL);
        } else if (addX == -2 && addY == 0) {
          addX = 0, addY = -1;
          puts(CGT_CORNER_BL);
        }

        x += addX;
        y += addY;
        break;

      default:
        puts("E");
    }
  }

  fflush(stdout);
  vts_asciiSet();
}

#endif /* ifndef CGT */
