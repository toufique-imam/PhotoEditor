//uniform mat4 u_Matrix;

attribute vec4 a_Position;
attribute vec2 a_TextureCoordinates;
//attribute vec4 a_Color;

varying vec2 v_TextureCoordinates;
//varying vec4 v_Color; //A varying is a special type of variable that blends the values given to it and sends these values to the fragment shader.
void main() {
//    v_Color = a_Color;
    v_TextureCoordinates = a_TextureCoordinates;
    gl_Position = a_Position;
    gl_PointSize = 10.0;
}