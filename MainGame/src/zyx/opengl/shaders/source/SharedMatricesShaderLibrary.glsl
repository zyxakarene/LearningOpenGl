layout (std140) uniform SharedMatrices
{
					// base alignment	// aligned offset
	mat4 proj;		// 16				// 0	(column 0)
					// 16				// 16	(column 1)
					// 16				// 32	(column 2)
					// 16				// 48	(column 3)
	mat4 view;		// 16				// 64	(column 0)
					// 16				// 80	(column 1)
					// 16				// 96	(column 2)
					// 16				// 128	(column 3)
	mat4 projView;	// 16				// 144	(column 0)
					// 16				// 160	(column 1)
					// 16				// 176	(column 2)
					// 16				// 192	(column 3)
}; 