#include <stdio.h>
#include <termios.h>
#include <unistd.h>
#include <fcntl.h>

int kbhit(void) {
  struct termios oldt, newt;
  int ch;
  int oldf;

  struct termios info;

  tcgetattr(STDIN_FILENO, &info); /* get current terminal attirbutes; 0 is the file descriptor for stdin */
  info.c_lflag &= ~ICANON;    /* disable canonical mode */
  info.c_cc[VMIN] = 0;            /* wait until at least one keystroke available */
  info.c_cc[VTIME] = 0;       /* no timeout */
  tcsetattr(STDIN_FILENO, TCSANOW, &info); /* set immediately */

  /*
     tcgetattr(STDIN_FILENO, &oldt);
     newt = oldt;
     newt.c_lflag &= ~(ICANON | ECHO);
     tcsetattr(STDIN_FILENO, TCSANOW, &newt);
     oldf = fcntl(STDIN_FILENO, F_GETFL, 0);
     fcntl(STDIN_FILENO, F_SETFL, oldf | O_NONBLOCK);
   */

  ch = getchar();

  //tcsetattr(STDIN_FILENO, TCSANOW, &oldt);
  //fcntl(STDIN_FILENO, F_SETFL, oldf);

  tcgetattr(0, &info);
  info.c_lflag |= ICANON;
  tcsetattr(0, TCSANOW, &info);

  if (ch != EOF) {
    ungetc(ch, stdin);
    return 1;
  }

  return 0;
}

int main() {
  while (1)
    if (kbhit()) putchar(getchar());
}
