#include <android/log.h>


void event_fire_cut_file_event(char *filename, int size);

void event_reg_cut_file_vent(void (*pFunction)(char *, int));