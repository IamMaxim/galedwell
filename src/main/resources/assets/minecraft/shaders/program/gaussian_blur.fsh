#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;
varying vec2 oneTexel;

uniform vec2 InSize;

uniform vec2 BlurDir;

const float weight[20] = float[] (0.19947114020071635, 0.19791884347237476, 0.19333405840142465, 0.18592754693488447, 0.17603266338214976, 0.16408048427518754, 0.15056871607740221, 0.13602749918927176, 0.12098536225957168, 0.10593832288784974, 0.09132454269451096, 0.07750613272914661, 0.06475879783294587, 0.05326913406529254, 0.043138659413255766, 0.03439313791334595, 0.02699548325659403, 0.02086049262816931, 0.01586982591783371, 0.011885950414956905);

void main() {
    vec4 blurred = vec4(0.0);
    float totalAlpha = 0.0;
    float totalWeight = 0;
    for(int i = -19; i <= 19; i++) {
        int index = i;
        if (index < 0) index = -index;
        vec4 s = texture2D(DiffuseSampler, texCoord + oneTexel * i * BlurDir) * weight[index];
        totalWeight += weight[index];

		// Accumulate smoothed blur
        blurred = blurred + s;
    }
    gl_FragColor = vec4(blurred.rgb / totalWeight, 1.0);
}
