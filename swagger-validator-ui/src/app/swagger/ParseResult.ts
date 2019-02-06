import {Path} from "./Path";

export interface ParseResult {
  url: string;
  paths: Path,
  errors: string[]
}
