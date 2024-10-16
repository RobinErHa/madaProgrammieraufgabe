{
  description = "A Nix-flake-based Java development environment";

  inputs = {
    #for unstable packages -> openjfx19 only in stable channel!
    #nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
    nixgl.url = "github:nix-community/nixGL";
  };

  outputs = { self, nixpkgs, nixgl, flake-utils, ... }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs {
          system = system;
          overlays = [ nixgl.overlay ];
        };
      in
      with pkgs; {
        devShells = {
          default = mkShell {
            buildInputs = [

              #javaVersion & openjfx Version:
              (pkgs.jdk21.override { enableJavaFX = true; })
              javaPackages.openjfx19

              gradle
              maven
              jdt-language-server
              metals
              glui
              libGL
              libglvnd
              xorg.libX11
              xorg.libXtst
              xorg.libXxf86vm
              gtk3
              glib
            ];
            shellHook = ''
              								export LD_LIBRARY_PATH=${pkgs.libGL}/lib:${pkgs.libglvnd}/lib:${pkgs.xorg.libX11}/lib:${pkgs.xorg.libXtst}/lib:${pkgs.xorg.libXxf86vm}/lib:${pkgs.gtk3}/lib:${pkgs.glib}/lib:$LD_LIBRARY_PATH
                            	echo "JAVA_HOME set to: $JAVA_HOME"
                              echo "GTK_LIB_PATH set to: $GTK_LIB_PATH"
                              echo "LD_LIBRARY_PATH set to: $LD_LIBRARY_PATH"
                              ls ${pkgs.libGL}/lib
                              ls ${pkgs.libglvnd}/lib
                              ls ${pkgs.xorg.libX11}/lib
            '';
          };
        };
      });
}
