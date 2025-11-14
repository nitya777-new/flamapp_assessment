const img = document.getElementById("frame") as HTMLImageElement;
const fpsEl = document.getElementById("fps")!;
const resEl = document.getElementById("res")!;

let lastTs = performance.now();
let frames = 0;
const sampleBase64 = "<PUT_BASE64_PNG_OR_JPEG_HERE>"; // paste a base64 processed frame saved from Android

img.src = `data:image/png;base64,${sampleBase64}`;
resEl.textContent = "640x480";

function tick() {
  frames++;
  const now = performance.now();
  if (now - lastTs >= 1000) {
    fpsEl.textContent = String(frames);
    frames = 0;
    lastTs = now;
  }
  requestAnimationFrame(tick);
}
requestAnimationFrame(tick);
