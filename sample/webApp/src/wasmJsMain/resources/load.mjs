import { instantiate } from './chiptextfield-sample.uninstantiated.mjs';

await wasmSetup;

instantiate({ skia: Module['asm'] });