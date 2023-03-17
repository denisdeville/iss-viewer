export const useCenter = () => useState<number[]>('center', () => [40,40])
export const useProjection = () => useState<string>('projection', () => 'EPSG:4326')