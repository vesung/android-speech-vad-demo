#include <stdint.h>
#include "event.h"

void(*cutfileListener)(char* filename, int size);


void event_fire_cut_file_event(char *filename, int size){
    if(cutfileListener == NULL){
        __android_log_print(ANDROID_LOG_ERROR, "event.c", "cutfileListener未注册\n");
        return;
    }

    (*cutfileListener)(filename, size);
}

void event_reg_cut_file_vent(void (*pFunction)(char *, int)){
    cutfileListener = pFunction;
}
