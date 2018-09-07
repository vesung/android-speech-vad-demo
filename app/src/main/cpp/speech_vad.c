#include <string.h>
#include "speech_vad.h"

simple_vad *vad;
struct periods *per;
struct cut_info *cut;

/**
 * 处理音频数据
 * @param frame 数据frame
 * @return -1：表示传入的数据frame为空
 *         -2：表示vad未初始化，请先执行vad_open
 *         0：
 */
int vad_process_frame(int16_t *frame){
    if(vad == NULL)
        return -2;

    process_frame(0, vad, frame, per, cut);

    return 1;
}

int vad_open(){
    vad = simple_vad_create();
    per = periods_create();
    cut = cut_info_create;
}

int vad_close(){
    if(vad != NULL)
        simple_vad_free(vad);
    if(per != NULL)
        periods_free(per);
    if(cut != NULL)
        cut_info_free(cut);
}
